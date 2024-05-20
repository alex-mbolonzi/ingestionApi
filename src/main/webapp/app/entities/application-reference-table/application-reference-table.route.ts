import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';
import { ApplicationReferenceTableService } from './application-reference-table.service';
import { ApplicationReferenceTableComponent } from './application-reference-table.component';
import { ApplicationReferenceTableDetailComponent } from './application-reference-table-detail.component';
import { ApplicationReferenceTableUpdateComponent } from './application-reference-table-update.component';
import { ApplicationReferenceTableDeletePopupComponent } from './application-reference-table-delete-dialog.component';
import { IApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';

@Injectable({ providedIn: 'root' })
export class ApplicationReferenceTableResolve implements Resolve<IApplicationReferenceTable> {
  constructor(private service: ApplicationReferenceTableService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApplicationReferenceTable> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApplicationReferenceTable>) => response.ok),
        map((applicationReferenceTable: HttpResponse<ApplicationReferenceTable>) => applicationReferenceTable.body),
      );
    }
    return of(new ApplicationReferenceTable());
  }
}

export const applicationReferenceTableRoute: Routes = [
  {
    path: '',
    component: ApplicationReferenceTableComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.applicationReferenceTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicationReferenceTableDetailComponent,
    resolve: {
      applicationReferenceTable: ApplicationReferenceTableResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.applicationReferenceTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicationReferenceTableUpdateComponent,
    resolve: {
      applicationReferenceTable: ApplicationReferenceTableResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.applicationReferenceTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicationReferenceTableUpdateComponent,
    resolve: {
      applicationReferenceTable: ApplicationReferenceTableResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.applicationReferenceTable.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

export const applicationReferenceTablePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApplicationReferenceTableDeletePopupComponent,
    resolve: {
      applicationReferenceTable: ApplicationReferenceTableResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ingestionApiApp.applicationReferenceTable.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup',
  },
];
