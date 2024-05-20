import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';
import { DatasetRoleDetailsService } from './dataset-role-details.service';

@Component({
  selector: 'jhi-dataset-role-details-delete-dialog',
  templateUrl: './dataset-role-details-delete-dialog.component.html',
})
export class DatasetRoleDetailsDeleteDialogComponent {
  datasetRoleDetails: IDatasetRoleDetails;

  constructor(
    protected datasetRoleDetailsService: DatasetRoleDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetRoleDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetRoleDetailsListModification',
        content: 'Deleted an datasetRoleDetails',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-role-details-delete-popup',
  template: '',
})
export class DatasetRoleDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetRoleDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetRoleDetailsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.datasetRoleDetails = datasetRoleDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-role-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-role-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
