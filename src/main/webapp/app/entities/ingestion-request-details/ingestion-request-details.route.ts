import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { IngestionRequestDetailsService } from './ingestion-request-details.service';
import { IngestionRequestDetailsComponent } from './ingestion-request-details.component';
import { IngestionRequestDetailsDetailComponent } from './ingestion-request-details-detail.component';
import { IngestionRequestDetailsUpdateComponent } from './ingestion-request-details-update.component';
import { IngestionRequestDetailsDeletePopupComponent } from './ingestion-request-details-delete-dialog.component';
import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';

@Injectable({ providedIn: 'root' })
export class IngestionRequestDetailsResolve implements Resolve<IIngestionRequestDetails> {
  constructor(private service: IngestionRequestDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIngestionRequestDetails> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IngestionRequestDetails>) => response.ok),
        map((ingestionRequestDetails: HttpResponse<IngestionRequestDetails>) => ingestionRequestDetails.body),
      );
    }
    return of(new IngestionRequestDetails());
  }
}

export const ingestionRequestDetailsRoute: Routes = [
  {
    path: '',
    component: IngestionRequestDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.ingestionRequestDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IngestionRequestDetailsDetailComponent,
    resolve: {
      ingestionRequestDetails: IngestionRequestDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.ingestionRequestDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IngestionRequestDetailsUpdateComponent,
    resolve: {
      ingestionRequestDetails: IngestionRequestDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.ingestionRequestDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IngestionRequestDetailsUpdateComponent,
    resolve: {
      ingestionRequestDetails: IngestionRequestDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.ingestionRequestDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const ingestionRequestDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IngestionRequestDetailsDeletePopupComponent,
    resolve: {
      ingestionRequestDetails: IngestionRequestDetailsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.ingestionRequestDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
