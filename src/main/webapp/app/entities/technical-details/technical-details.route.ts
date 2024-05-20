import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TechnicalDetails } from 'app/shared/model/technical-details.model';
import { TechnicalDetailsService } from './technical-details.service';
import { TechnicalDetailsComponent } from './technical-details.component';
import { TechnicalDetailsDetailComponent } from './technical-details-detail.component';
import { TechnicalDetailsUpdateComponent } from './technical-details-update.component';
import { TechnicalDetailsDeletePopupComponent } from './technical-details-delete-dialog.component';
import { ITechnicalDetails } from 'app/shared/model/technical-details.model';

@Injectable({ providedIn: 'root' })
export class TechnicalDetailsResolve implements Resolve<ITechnicalDetails> {
  constructor(private service: TechnicalDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITechnicalDetails> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TechnicalDetails>) => response.ok),
        map((technicalDetails: HttpResponse<TechnicalDetails>) => technicalDetails.body),
      );
    }
    return of(new TechnicalDetails());
  }
}

export const technicalDetailsRoute: Routes = [
  {
    path: '',
    component: TechnicalDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.technicalDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TechnicalDetailsDetailComponent,
    resolve: {
      technicalDetails: TechnicalDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.technicalDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TechnicalDetailsUpdateComponent,
    resolve: {
      technicalDetails: TechnicalDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.technicalDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TechnicalDetailsUpdateComponent,
    resolve: {
      technicalDetails: TechnicalDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.technicalDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const technicalDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TechnicalDetailsDeletePopupComponent,
    resolve: {
      technicalDetails: TechnicalDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.technicalDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
